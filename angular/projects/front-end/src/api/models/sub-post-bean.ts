import { UploadedFileBean } from './uploaded-file-bean';
import { UserBean } from './user-bean';

export interface SubPostBean {
  readonly id: number;
  readonly title: string;
  readonly description: string;
  readonly content: UploadedFileBean;
  readonly contributors: UserBean[];
  readonly created: string;
  readonly updated: string;
}
