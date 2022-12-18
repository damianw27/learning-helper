import { FileBean } from './file-bean';

export interface QuestionBean {
	readonly id: number;
	readonly subPostId: number;
	readonly content: FileBean;
	readonly answers: string;
}
