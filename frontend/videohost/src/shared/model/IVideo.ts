export default interface IVideo {
  userId: string
  title: string
  description: string
  s3Key: string
  views: number
  comments: {
    commentId: string
    text: string
    userId: string
    creationDate: string
    author: string
    isUserComment: boolean
  }[]
  reactions: {
    likeCount: number
    dislikeCount: number
  }
  uploadDate: string
  tags: string[]
  videoId: string
  author: string
  isVideoHidden: boolean
  userReaction?: 'LIKE' | 'DISLIKE' | null
  fileName: string
}
