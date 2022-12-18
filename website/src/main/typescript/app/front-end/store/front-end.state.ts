import { AuthState } from './auth/auth.state';
import { PostsState } from './posts/posts.state';
import { ExternalApiState } from './external-api/external-api.state';
import { QuestionsState } from './questions/questions.state';
import { SubPostsState } from './sub-posts/sub-posts.state';

export interface FrontEndState {
  readonly auth: AuthState;
  readonly externalApi: ExternalApiState;
  readonly posts: PostsState;
  readonly questions: QuestionsState;
  readonly subPosts: SubPostsState;
}
