import { createEntityAdapter } from '@ngrx/entity';
import { SubPostBean } from '../../api/models/sub-post-bean';

export const subPostsAdapter = createEntityAdapter<SubPostBean>({
  selectId: model => model.id,
});
