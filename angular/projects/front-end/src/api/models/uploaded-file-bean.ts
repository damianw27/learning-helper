import { UserBean } from './user-bean';

export interface UploadedFileBean {
  readonly name: string;
  readonly originalName: string;
  readonly type: string;
  readonly url: string;
  readonly size: number;
  readonly owner: UserBean;
}
