import { Component } from '@angular/core';
import { DefaultUserService } from './api/default-user.service';
import { ExternalApi } from './front-end/api/external-api';
import { DefaultFileService } from './api/default-file.service';
import { DefaultPostService } from './api/default-post.service';
import { DefaultQuestionService } from './api/default-question.service';
import { DefaultSubPostService } from './api/default-sub-post.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss'],
})
export class AppComponent {
	public externalApi: ExternalApi = {
		fileService: this.fileService,
		postService: this.postService,
		questionService: this.questionService,
		subPostService: this.subPostService,
		userService: this.userService,
	};

	public constructor(
		private userService: DefaultUserService,
		private fileService: DefaultFileService,
		private postService: DefaultPostService,
		private questionService: DefaultQuestionService,
		private subPostService: DefaultSubPostService
	) {
		console.log('chuj');
	}
}
