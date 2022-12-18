import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CdkDragDrop } from '@angular/cdk/drag-drop';
import { SubPostsSelectors } from '../../store/sub-posts/sub-posts.selector';
import { Store } from '@ngrx/store';
import { FrontEndState } from '../../store/front-end.state';
import { subPostsAction } from '../../store/sub-posts/sub-posts.action';
import { FormGroup } from '@angular/forms';
import { PostBean } from '../../api/models/post-bean';
import { map, tap } from 'rxjs';

@Component({
	selector: 'app-sub-posts',
	templateUrl: './sub-posts.component.html',
	styleUrls: ['./sub-posts.component.scss'],
})
export class SubPostsComponent {
	@Input()
	public post?: PostBean;

	@Input()
	public isEditMode = false;

	public subPosts$ = this.subPostsSelectors.subPosts().pipe(
		map(subPosts =>
			subPosts.filter(subPost => subPost.postId === this.post?.id)
		),
		tap(subPost => console.log(subPost))
	);

	public constructor(
		private httpClient: HttpClient,
		private changeDetectorRef: ChangeDetectorRef,
		private subPostsSelectors: SubPostsSelectors,
		private store: Store<FrontEndState>
	) {}

	public dropLessonItem(event: CdkDragDrop<FormGroup[]>) {
		// moveItemInArray(this.lessons, event.previousIndex, event.currentIndex);
		// this.changeDetectorRef.detectChanges();
	}

	public deleteSubPost(subPostId: number): void {
		this.store.dispatch(subPostsAction.delete({ subPostId }));
	}
}
