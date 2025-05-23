package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ApplicationData;
import org.example.exception.DataTransferExportException;
import org.example.exception.DataTransferImportException;
import org.example.model.*;
import org.example.repository.*;
import org.example.service.api.DataTransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class DataTransferServiceImpl implements DataTransferService {

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    @Override
    @Transactional
    public byte[] exportAllData(DataTransferService.Format format) {
        Map<String, Object> allData = new LinkedHashMap<>();

        try {
            allData.put("users", userRepository.findAll());
            allData.put("videos", videoRepository.findAll());
            allData.put("subscriptions", subscriptionRepository.findAll());
            allData.put("comments", commentRepository.findAll());
            allData.put("reactions", reactionRepository.findAll());

            return switch (format) {
                case JSON -> objectMapper.writeValueAsBytes(allData);
                case XML -> xmlMapper.writeValueAsBytes(allData);
            };
        } catch (IOException e) {
            throw new DataTransferExportException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void importAllData(byte[] data, Format format) {
        try {
            userRepository.deleteAll();
            videoRepository.deleteAll();
            subscriptionRepository.deleteAll();
            commentRepository.deleteAll();
            reactionRepository.deleteAll();

            ApplicationData importedData = parseData(data, format);
            resolveReferences(importedData);
            if (importedData.getUsers() != null) {
                userRepository.saveAll(importedData.getUsers());
            }

            if (importedData.getVideos() != null) {
                videoRepository.saveAll(importedData.getVideos());
            }

            if (importedData.getSubscriptions() != null) {
                subscriptionRepository.saveAll(importedData.getSubscriptions());
            }

            if (importedData.getComments() != null) {
                commentRepository.saveAll(importedData.getComments());
            }

            if (importedData.getReactions() != null) {
                reactionRepository.saveAll(importedData.getReactions());
            }
        } catch (IOException e) {
            throw new DataTransferImportException("Failed to import data: " + e.getMessage());
        }
    }

    private ApplicationData parseData(byte[] data, Format format) throws IOException {
        if (format == Format.XML) {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Map<String, Object> rawData = xmlMapper.readValue(data, new TypeReference<Map<String, Object>>() {});

            return convertRawDataToApplicationData(rawData);
        } else {
            return objectMapper.readValue(data, ApplicationData.class);
        }
    }

    private void resolveReferences(ApplicationData applicationData) {
        Map<String, UserData> userMap = applicationData.getUsers().stream()
                .collect(Collectors.toMap(UserData::getId, Function.identity()));

        Map<String, VideoData> videoMap = applicationData.getVideos().stream()
                .collect(Collectors.toMap(VideoData::getId, Function.identity()));

        applicationData.getVideos().forEach(video -> {
            if (video.getUser() != null) {
                video.setUser(userMap.get(video.getUser().getId()));
            }
        });

        applicationData.getSubscriptions().forEach(sub -> {
            if (sub.getFollower() != null) {
                sub.setFollower(userMap.get(sub.getFollower().getId()));
            }
            if (sub.getFollowing() != null) {
                sub.setFollowing(userMap.get(sub.getFollowing().getId()));
            }
        });

        applicationData.getComments().forEach(comment -> {
            if (comment.getUser() != null) {
                comment.setUser(userMap.get(comment.getUser().getId()));
            }
            if (comment.getVideo() != null) {
                comment.setVideo(videoMap.get(comment.getVideo().getId()));
            }
        });

        applicationData.getReactions().forEach(reaction -> {
            if (reaction.getUser() != null) {
                reaction.setUser(userMap.get(reaction.getUser().getId()));
            }
            if (reaction.getVideo() != null) {
                reaction.setVideo(videoMap.get(reaction.getVideo().getId()));
            }
        });
    }

    private ApplicationData convertRawDataToApplicationData(Map<String, Object> rawData) {
        ApplicationData applicationData = new ApplicationData();

        Function<String, List<Map<String, Object>>> extractList = key -> {
            Object value = rawData.get(key);
            if (value instanceof Map) {
                return Collections.singletonList((Map<String, Object>) value);
            } else if (value instanceof List) {
                return (List<Map<String, Object>>) value;
            }
            return Collections.emptyList();
        };

        List<Map<String, Object>> rawUsers = extractList.apply("users");
        applicationData.setUsers(rawUsers.stream()
                .map(this::convertRawUser)
                .collect(Collectors.toList()));

        List<Map<String, Object>> rawVideos = extractList.apply("videos");
        applicationData.setVideos(rawVideos.stream()
                .map(this::convertRawVideo)
                .collect(Collectors.toList()));

        List<Map<String, Object>> rawSubscriptions = extractList.apply("subscriptions");
        applicationData.setSubscriptions(rawSubscriptions.stream()
                .map(this::convertRawSubscription)
                .collect(Collectors.toList()));

        List<Map<String, Object>> rawComments = extractList.apply("comments");
        applicationData.setComments(rawComments.stream()
                .map(this::convertRawComment)
                .collect(Collectors.toList()));

        List<Map<String, Object>> rawReactions = extractList.apply("reactions");
        applicationData.setReactions(rawReactions.stream()
                .map(this::convertRawReaction)
                .collect(Collectors.toList()));

        return applicationData;
    }

    private UserData convertRawUser(Map<String, Object> rawUser) {
        UserData user = new UserData();
        user.setId(rawUser.get("id").toString());
        user.setUsername((String) rawUser.get("username"));
        user.setPassword((String) rawUser.get("password"));
        user.setEmail((String) rawUser.get("email"));

        if (rawUser.get("registrationDate") != null) {
            user.setRegistrationDate(LocalDateTime.parse(rawUser.get("registrationDate").toString()));
        }

        if (rawUser.get("subscriberCount") != null) {
            user.setSubscriberCount(Integer.parseInt(rawUser.get("subscriberCount").toString()));
        }

        user.setBlocked(Boolean.parseBoolean(rawUser.get("blocked").toString()));

        if (rawUser.get("roles") != null) {
            Object rolesObj = rawUser.get("roles");
            Set<RoleEnum> roles = new HashSet<>();

            if (rolesObj instanceof List) {
                ((List<?>) rolesObj).forEach(role -> roles.add(RoleEnum.valueOf(role.toString())));
            } else if (rolesObj instanceof Map) {
                ((Map<?, ?>) rolesObj).values().forEach(role -> roles.add(RoleEnum.valueOf(role.toString())));
            }

            user.setRoles(roles);
        }

        return user;
    }

    private VideoData convertRawVideo(Map<String, Object> rawVideo) {
        VideoData video = VideoData.builder()
                .id(rawVideo.get("id").toString())
                .title((String) rawVideo.get("title"))
                .description((String) rawVideo.get("description"))
                .s3Key((String) rawVideo.get("s3Key"))
                .filename((String) rawVideo.get("filename"))
                .build();

        if (rawVideo.get("views") != null) {
            video.setViews(Integer.parseInt(rawVideo.get("views").toString()));
        }

        if (rawVideo.get("uploadDate") != null) {
            video.setUploadDate(LocalDateTime.parse(rawVideo.get("uploadDate").toString()));
        }

        if (rawVideo.get("tags") != null) {
            Object tagsObj = rawVideo.get("tags");
            List<String> tags = new ArrayList<>();

            if (tagsObj instanceof List) {
                tags = ((List<?>) tagsObj).stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
            } else if (tagsObj instanceof Map) {
                tags.addAll(((Map<?, ?>) tagsObj).values().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()));
            } else if (tagsObj instanceof String) {
                tags.add(tagsObj.toString());
            }

            video.setTags(tags);
        }

        if (rawVideo.get("isVideoHidden") != null) {
            video.setVideoHidden(Boolean.parseBoolean(rawVideo.get("isVideoHidden").toString()));
        }

        if (rawVideo.get("user") != null) {
            Object userObj = rawVideo.get("user");
            if (userObj instanceof Map) {
                Map<String, Object> rawUser = (Map<String, Object>) userObj;
                UserData user = convertRawUser(rawUser);
                video.setUser(user);
            }
        }
        return video;
    }

    private SubscriptionData convertRawSubscription(Map<String, Object> rawSubscription) {
        SubscriptionData subscription = SubscriptionData.builder()
                .id(rawSubscription.get("id").toString())
                .build();

        if (rawSubscription.get("createdDate") != null) {
            subscription.setCreatedDate(LocalDateTime.parse(rawSubscription.get("createdDate").toString()));
        }

        if (rawSubscription.get("follower") != null) {
            Object userObj = rawSubscription.get("follower");
            if (userObj instanceof Map) {
                Map<String, Object> rawUser = (Map<String, Object>) userObj;
                UserData user = convertRawUser(rawUser);
                subscription.setFollower(user);
            }
        }

        if (rawSubscription.get("following") != null) {
            Object userObj = rawSubscription.get("following");
            if (userObj instanceof Map) {
                Map<String, Object> rawUser = (Map<String, Object>) userObj;
                UserData user = convertRawUser(rawUser);
                subscription.setFollowing(user);
            }
        }

        return subscription;
    }

    private CommentData convertRawComment(Map<String, Object> rawComment) {
        CommentData comment = CommentData.builder()
                .id(rawComment.get("id").toString())
                .text((String) rawComment.get("text"))
                .build();

        if (rawComment.get("creationDate") != null) {
            comment.setCreationDate(LocalDateTime.parse(rawComment.get("creationDate").toString()));
        }

        if (rawComment.get("user") != null) {
            Object userObj = rawComment.get("user");
            if (userObj instanceof Map) {
                Map<String, Object> rawUser = (Map<String, Object>) userObj;
                UserData user = convertRawUser(rawUser);
                comment.setUser(user);
            }
        }
        return comment;
    }

    private ReactionData convertRawReaction(Map<String, Object> rawReaction) {
        ReactionData reaction = ReactionData.builder()
                .id(rawReaction.get("id").toString())
                .type(ReactionEnum.valueOf(rawReaction.get("type").toString()))
                .build();

        if (rawReaction.get("timestamp") != null) {
            reaction.setTimestamp(LocalDateTime.parse(rawReaction.get("timestamp").toString()));
        }

        if (rawReaction.get("user") != null) {
            Object userObj = rawReaction.get("user");
            if (userObj instanceof Map) {
                Map<String, Object> rawUser = (Map<String, Object>) userObj;
                UserData user = convertRawUser(rawUser);
                reaction.setUser(user);
            }
        }
        return reaction;
    }
}
