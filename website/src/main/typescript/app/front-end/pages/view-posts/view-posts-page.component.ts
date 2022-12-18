import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { FrontEndState } from '../../store/front-end.state';
import { postsAction } from '../../store/posts/posts.action';
import { PostsSelectors } from '../../store/posts/posts.selector';

@Component({
	selector: 'app-posts',
	templateUrl: './view-posts-page.component.html',
	styleUrls: ['./view-posts-page.component.css'],
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ViewPostsPageComponent implements OnInit {
	public readonly posts$ = this.postsSelectors.posts();

	public constructor(
		private store: Store<FrontEndState>,
		private postsSelectors: PostsSelectors
	) {}

	public ngOnInit(): void {
		this.store.dispatch(postsAction.fetchPagesCount({ pageSize: 20 }));
	}
}
