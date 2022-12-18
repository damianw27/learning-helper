import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SubPostBean } from '../../api/models/sub-post-bean';
import { Store } from '@ngrx/store';
import { FrontEndState } from '../../store/front-end.state';
import { subPostsAction } from '../../store/sub-posts/sub-posts.action';

@Component({
	selector: 'app-sub-post-element',
	templateUrl: './sub-post-element.component.html',
	styleUrls: ['./sub-post-element.component.scss'],
})
export class SubPostElementComponent {
	@Input()
	public subPostBean?: SubPostBean;

	@Input()
	public isEditMode = false;

	public constructor(private store: Store<FrontEndState>) {}

	public onDeleteClick(subPostId: number) {
		this.store.dispatch(subPostsAction.delete({ subPostId }));
	}
}
