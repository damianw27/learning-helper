import { FileService } from './services/file-service';
import { PostService } from './services/post-service';
import { QuestionService } from './services/question-service';
import { SubPostService } from './services/sub-post-service';
import { UserService } from './services/user-service';
import { EMPTY } from 'rxjs';

export interface ExternalApi {
	readonly fileService: FileService;
	readonly postService: PostService;
	readonly questionService: QuestionService;
	readonly subPostService: SubPostService;
	readonly userService: UserService;
}

export const defaultExternalApi = {
	fileService: {
		fileInfo: () => EMPTY,
		uploadFile: () => EMPTY,
		fetchFile: () => EMPTY,
	},
	postService: {
		create: () => EMPTY,
		delete: () => EMPTY,
		fetchById: () => EMPTY,
		fetchPage: () => EMPTY,
		pagesCount: () => EMPTY,
		search: () => EMPTY,
		update: () => EMPTY,
	},
	questionService: {
		create: () => EMPTY,
		delete: () => EMPTY,
		update: () => EMPTY,
		fetch: () => EMPTY,
	},
	subPostService: {
		create: () => EMPTY,
		delete: () => EMPTY,
		fetchPage: () => EMPTY,
		pagesCount: () => EMPTY,
		update: () => EMPTY,
	},
	userService: {
		create: () => EMPTY,
		update: () => EMPTY,
		logout: () => EMPTY,
		login: () => EMPTY,
	},
};
