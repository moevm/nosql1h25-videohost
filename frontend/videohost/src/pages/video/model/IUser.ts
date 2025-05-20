export default interface IUser {
  username: string
  email: string
  registrationDate: string
  subscriberCount: number
  blocked: boolean
  roles: ('ADMIN' | 'USER')[]
  isUserSubscribe: boolean
  videos: {
    videoId: string
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
    }[]
    reactions: {
      likeCount: number
      dislikeCount: number
    }
    uploadDate: string
    tags: string[]
    isVideoHidden: boolean
  }[]
}
