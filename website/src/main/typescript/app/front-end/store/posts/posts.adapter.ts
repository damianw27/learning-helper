import { createEntityAdapter } from '@ngrx/entity';
import { PostBean } from '../../api/models/post-bean';

export const postsAdapter = createEntityAdapter<PostBean>({
  selectId: model => model.id,
});
