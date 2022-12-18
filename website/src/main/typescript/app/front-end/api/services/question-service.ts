import { Observable } from 'rxjs';
import { QuestionBean } from '../models/question-bean';
import { QuestionCreateBean } from '../models/question-create-bean';
import { QuestionUpdateBean } from '../models/question-update-bean';
import { AuthContext } from '../models/auth-context';

export interface QuestionService {
	readonly fetch: (
		subPageId: number,
		authContext: AuthContext
	) => Observable<QuestionBean[]>;
	readonly create: (
		questionCreateBean: QuestionCreateBean,
		authContext: AuthContext
	) => Observable<QuestionBean>;
	readonly update: (
		questionUpdateBean: QuestionUpdateBean,
		authContext: AuthContext
	) => Observable<QuestionBean>;
	readonly delete: (
		questionId: number,
		authContext: AuthContext
	) => Observable<unknown>;
}
