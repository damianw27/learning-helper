import { UserBean } from './user-bean';
import { SubPostBean } from './sub-post-bean';
import { TagBean } from './tag-bean';
import { FileBean } from './file-bean';

export interface PostBean {
	readonly id: number;
	readonly title: string;
	readonly description: FileBean;
	readonly contributors: UserBean[];
	readonly subPosts: SubPostBean[];
	readonly tags: TagBean[];
	readonly created: string;
	readonly updated: string;
}
