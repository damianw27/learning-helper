import { EntityState } from '@ngrx/entity';
import { SubPostBean } from '../../api/models/sub-post-bean';

export interface SubPostsState extends EntityState<SubPostBean> {}
