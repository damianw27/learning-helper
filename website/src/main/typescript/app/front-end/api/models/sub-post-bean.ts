import { UserBean } from './user-bean';
import { FileBean } from './file-bean';

export interface SubPostBean {
	readonly id: number;
	readonly postId: number;
	readonly title: string;
	readonly description: string;
	readonly content: FileBean;
	readonly contributors: UserBean[];
	readonly created: string;
	readonly updated: string;
}
