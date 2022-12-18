import { EntityState } from '@ngrx/entity';
import { PostBean } from '../../api/models/post-bean';

export interface PostsState extends EntityState<PostBean> {
  readonly pagesCount: number;
  readonly loadedPagesCount: number;
  readonly pageSize: number;
}
