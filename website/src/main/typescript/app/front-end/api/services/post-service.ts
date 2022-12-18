import { Observable } from 'rxjs';
import { PostBean } from '../models/post-bean';
import { PostCreateBean } from '../models/post-create-bean';
import { PostUpdateBean } from '../models/post-update-bean';
import { AuthContext } from '../models/auth-context';

export interface PostService {
  readonly pagesCount: (chunkSize: number, authContext: AuthContext) => Observable<number>;
  readonly fetchPage: (
    index: number,
    size: number,
    authContext: AuthContext
  ) => Observable<PostBean[]>;
  readonly search: (query: string, authContext: AuthContext) => Observable<PostBean[]>;
  readonly fetchById: (pageId: number, authContext: AuthContext) => Observable<PostBean>;
  readonly create: (
    postCreateBean: PostCreateBean,
    authContext: AuthContext
  ) => Observable<PostBean>;
  readonly update: (
    postUpdateBean: PostUpdateBean,
    authContext: AuthContext
  ) => Observable<PostBean>;
  readonly delete: (postId: number, authContext: AuthContext) => Observable<unknown>;
}
