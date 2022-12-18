import {
	ChangeDetectorRef,
	Component,
	Input,
	OnChanges,
	SimpleChanges,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { FrontEndState } from '../../store/front-end.state';
import { SubPostCreateBean } from '../../api/models/sub-post-create-bean';
import { subPostsAction } from '../../store/sub-posts/sub-posts.action';

@Component({
	selector: 'app-sub-post-create-form',
	templateUrl: './sub-post-create-form.component.html',
	styleUrls: ['./sub-post-create-form.component.scss'],
})
export class SubPostCreateFormComponent implements OnChanges {
	@Input()
	public postId?: number;

	public subPostForm = this.formBuilder.group({
		postId: [this.postId, Validators.required],
		title: ['', Validators.required],
	});

	public constructor(
		private formBuilder: FormBuilder,
		private store: Store<FrontEndState>,
		private changeDetectorRef: ChangeDetectorRef
	) {}

	public createSubPost(): void {
		const postId = this.subPostForm.controls.postId.value ?? -1;
		const title = this.subPostForm.controls.title.value ?? '';

		const subPostCreateBean: SubPostCreateBean = {
			postId,
			title,
		};

		this.store.dispatch(subPostsAction.create({ subPostCreateBean }));
		this.resetFormValues();
	}

	public ngOnChanges(changes: SimpleChanges): void {
		const postIdChanged = changes['postId']?.currentValue;

		if (postIdChanged === undefined) {
			return;
		}

		this.subPostForm.controls.postId.setValue(postIdChanged, {
			emitEvent: false,
		});
		this.subPostForm.controls.title.setValue('', { emitEvent: false });
		this.changeDetectorRef.detectChanges();
	}

	public getTitleClassName(): string {
		return this.isTitleInvalid() ? 'form-control is-invalid' : 'form-control';
	}

	private isTitleInvalid(): boolean {
		return this.subPostForm.touched && this.subPostForm.invalid;
	}

	private resetFormValues(): void {
		this.subPostForm.reset();

		this.subPostForm.setValue({
			postId: this.postId,
			title: '',
		});

		this.changeDetectorRef.detectChanges();
	}
}
