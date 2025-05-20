package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.example.service.api.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoMigration implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        mongoTemplate.dropCollection("subscriptions");
        mongoTemplate.dropCollection("reactions");
        mongoTemplate.dropCollection("comments");
        mongoTemplate.dropCollection("videos");
        mongoTemplate.dropCollection("users");

        boolean userExists = mongoTemplate.exists(
                Query.query(Criteria.where("username").is("test")),
                "users"
        );

        boolean user2Exists = mongoTemplate.exists(
                Query.query(Criteria.where("username").is("test1")),
                "users"
        );

        if (!userExists) {
            UserData testUser = new UserData();
            testUser.getRoles().add(RoleEnum.ADMIN);
            testUser.setBlocked(false);
            testUser.setUsername("test");
            testUser.setSubscriberCount(1);
            testUser.setPassword(passwordEncoder.encode("test"));

            mongoTemplate.insert(testUser, "users");
            log.info("Тестовый пользователь добавлен!");

            /*Resource resource = new ClassPathResource("data/sample-5s.mp4");
            File file = resource.getFile();*/

            File file = new File("/app/resources/data/sample-5s.mp4");
            String fileName = UUID.randomUUID() + "-" + file.getName();
            MultipartFile multipartFile = new MockMultipartFile(
                    file.getName(),
                    file.getName(),
                    Files.probeContentType(file.toPath()),
                    Files.readAllBytes(file.toPath())
            );
            VideoData testVideo = new VideoData();
            testVideo.setTags(Arrays.asList("Видео", "Улица"));
            testVideo.setDescription("Улица");
            testVideo.setTitle("Видео");
            testVideo.setViews(10);

            String url = s3Service.uploadFile(fileName, multipartFile);

            testVideo.setFilename(fileName);
            testVideo.setS3Key(cleanS3UrlWithRegex(url));
            testVideo.setUser(testUser);
            mongoTemplate.insert(testVideo, "videos");

            testVideo.setUploadDate(LocalDate.parse("04.03.2025", formatter).atStartOfDay());

            Query query = new Query(Criteria.where("_id").is(testVideo.getId()));
            Update update = new Update().set("uploadDate", testVideo.getUploadDate());
            mongoTemplate.updateFirst(query, update, VideoData.class, "videos");
            log.info("Тестовое видео добавлено!");

            CommentData testComment = new CommentData();
            testComment.setText("Test 1");
            testComment.setVideo(testVideo);
            testComment.setUser(testUser);
            mongoTemplate.insert(testComment, "comments");
            log.info("Тестовый комментарий добавлен!");

            ReactionData testReaction = new ReactionData();
            testReaction.setType(ReactionEnum.LIKE);
            testReaction.setVideo(testVideo);
            testReaction.setUser(testUser);
            mongoTemplate.insert(testReaction, "reactions");
            log.info("Тестовая реакция добавлена!");

            if (!user2Exists) {
                UserData test2User = new UserData();
                test2User.getRoles().add(RoleEnum.USER);
                test2User.setBlocked(false);
                test2User.setUsername("test1");
                test2User.setPassword(passwordEncoder.encode("test"));

                mongoTemplate.insert(test2User, "users");
                log.info("Тестовый пользователь добавлен!");

                /*Resource resource2 = new ClassPathResource("data/sample-5s.mp4");
                File file2 = resource2.getFile();*/
                File file2 = new File("/app/resources/data/sample-5s.mp4");
                String fileName2 = UUID.randomUUID() + "-" + file2.getName();
                MultipartFile multipartFile2 = new MockMultipartFile(
                        file2.getName(),
                        file2.getName(),
                        Files.probeContentType(file2.toPath()),
                        Files.readAllBytes(file2.toPath())
                );
                VideoData testVideo2 = new VideoData();
                testVideo2.setTags(Arrays.asList("Видео2", "Улица2"));
                testVideo2.setDescription("Улица2");
                testVideo2.setTitle("Видео2");
                testVideo2.setViews(100);

                String url2 = s3Service.uploadFile(fileName2, multipartFile2);

                testVideo2.setFilename(fileName2);
                testVideo2.setS3Key(cleanS3UrlWithRegex(url2));
                testVideo2.setUser(test2User);
                mongoTemplate.insert(testVideo2, "videos");
                testVideo2.setUploadDate(LocalDate.parse("01.20.2025", formatter).atStartOfDay());
                Query query2 = new Query(Criteria.where("_id").is(testVideo2.getId()));
                Update update2 = new Update().set("uploadDate", testVideo2.getUploadDate());
                mongoTemplate.updateFirst(query2, update2, VideoData.class, "videos");
                log.info("Тестовое видео добавлено!");

                CommentData testComment2 = new CommentData();
                testComment2.setText("Test 2");
                testComment2.setVideo(testVideo2);
                testComment2.setUser(test2User);
                CommentData testComment3 = new CommentData();
                testComment2.setText("Test 3");
                testComment2.setVideo(testVideo);
                testComment2.setUser(test2User);
                mongoTemplate.insert(testComment2, "comments");
                mongoTemplate.insert(testComment3, "comments");
                log.info("Тестовый комментарий добавлен!");

                ReactionData testReaction2 = new ReactionData();
                testReaction2.setType(ReactionEnum.DISLIKE);
                testReaction2.setVideo(testVideo);
                testReaction2.setUser(test2User);
                mongoTemplate.insert(testReaction2, "reactions");
                ReactionData testReaction3 = new ReactionData();
                testReaction3.setType(ReactionEnum.LIKE);
                testReaction3.setVideo(testVideo2);
                testReaction3.setUser(test2User);
                mongoTemplate.insert(testReaction3, "reactions");
                log.info("Тестовая реакция добавлена!");

                SubscriptionData subscriptionData = new SubscriptionData();
                subscriptionData.setFollowing(testUser);
                subscriptionData.setFollower(test2User);
                mongoTemplate.insert(subscriptionData, "subscriptions");
                log.info("Тестовая подписка добавлена!");
            }
        }
        for (int i = 3; i <= 7; i++) {
            String username = "test" + i;
            boolean userIExists = mongoTemplate.exists(
                    Query.query(Criteria.where("username").is(username)),
                    "users"
            );

            if (!userIExists) {
                UserData testIUser = new UserData();
                testIUser.getRoles().add(i % 2 == 0 ? RoleEnum.USER : RoleEnum.ADMIN);
                testIUser.setBlocked(false);
                testIUser.setUsername(username);
                testIUser.setSubscriberCount(i);
                testIUser.setPassword(passwordEncoder.encode("test" + i));

                mongoTemplate.insert(testIUser, "users");
                log.info("Тестовый пользователь {} добавлен!", username);

                /*Resource resource = new ClassPathResource("data/sample-5s.mp4");
                File file = resource.getFile();*/
                File file = new File("/app/resources/data/sample-5s.mp4");
                String fileName = UUID.randomUUID() + "-" + file.getName();
                MultipartFile multipartFile = new MockMultipartFile(
                        file.getName(),
                        file.getName(),
                        Files.probeContentType(file.toPath()),
                        Files.readAllBytes(file.toPath())
                );
                VideoData testIVideo = new VideoData();
                testIVideo.setTags(Arrays.asList("Видео" + i, "Тег" + i, "Категория" + i));
                testIVideo.setDescription("Описание видео " + i);
                testIVideo.setTitle("Видео " + i);
                testIVideo.setViews(i * 10);

                String url = s3Service.uploadFile(fileName, multipartFile);

                testIVideo.setFilename(fileName);
                testIVideo.setS3Key(cleanS3UrlWithRegex(url));
                testIVideo.setUser(testIUser);
                mongoTemplate.insert(testIVideo, "videos");
                log.info("Тестовое видео {} добавлено!", i);

                CommentData testIComment1 = new CommentData();
                testIComment1.setText("Комментарий " + i + " от " + username);
                testIComment1.setVideo(testIVideo);
                testIComment1.setUser(testIUser);
                mongoTemplate.insert(testIComment1, "comments");

                CommentData testIComment2 = new CommentData();
                testIComment2.setText("Второй комментарий " + i);
                testIComment2.setVideo(testIVideo);
                testIComment2.setUser(testIUser);
                mongoTemplate.insert(testIComment2, "comments");
                log.info("Тестовые комментарии для пользователя {} добавлены!", username);

                ReactionData testIReaction1 = new ReactionData();
                testIReaction1.setType(i % 2 == 0 ? ReactionEnum.LIKE : ReactionEnum.DISLIKE);
                testIReaction1.setVideo(testIVideo);
                testIReaction1.setUser(testIUser);
                mongoTemplate.insert(testIReaction1, "reactions");

                if (i > 3) {
                    UserData firstUser = mongoTemplate.findOne(
                            Query.query(Criteria.where("username").is("test")),
                            UserData.class, "users");
                    VideoData firstVideo = mongoTemplate.findOne(
                            Query.query(Criteria.where("title").is("Видео")),
                            VideoData.class, "videos");

                    ReactionData testIReaction2 = new ReactionData();
                    testIReaction2.setType(i % 3 == 0 ? ReactionEnum.LIKE : ReactionEnum.DISLIKE);
                    testIReaction2.setVideo(firstVideo);
                    testIReaction2.setUser(testIUser);
                    mongoTemplate.insert(testIReaction2, "reactions");
                }
                log.info("Тестовые реакции для пользователя {} добавлены!", username);

                if (i > 3) {
                    UserData prevUser = mongoTemplate.findOne(
                            Query.query(Criteria.where("username").is("test" + (i-1))),
                            UserData.class, "users");

                    SubscriptionData subscriptionData1 = new SubscriptionData();
                    subscriptionData1.setFollowing(prevUser);
                    subscriptionData1.setFollower(testIUser);
                    mongoTemplate.insert(subscriptionData1, "subscriptions");

                    UserData firstUser = mongoTemplate.findOne(
                            Query.query(Criteria.where("username").is("test")),
                            UserData.class, "users");

                    SubscriptionData subscriptionData2 = new SubscriptionData();
                    subscriptionData2.setFollowing(firstUser);
                    subscriptionData2.setFollower(testIUser);
                    mongoTemplate.insert(subscriptionData2, "subscriptions");

                    log.info("Тестовые подписки для пользователя {} добавлены!", username);
                }
            }
        }
    }

    private String cleanS3UrlWithRegex(String originalUrl) {
        if (originalUrl == null || originalUrl.isEmpty()) {
            return originalUrl;
        }

        return originalUrl
                .replaceFirst("://s3:", "://localhost:")
                .replaceAll("\\?.*$", "");
    }
}
