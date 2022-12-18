import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatestWith, firstValueFrom, map, Observable, tap } from 'rxjs';
import { PostsSelectors } from '../../store/posts/posts.selector';
import { PostBean } from '../../api/models/post-bean';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { typedFilter } from '../../operator/typed-filter-operator';

@Component({
	selector: 'app-view-post',
	templateUrl: './view-post-page.component.html',
	styleUrls: ['./view-post-page.component.scss'],
})
export class ViewPostPageComponent implements OnInit {
	public post?: PostBean;

	public postForm = this.formBuilder.group({
		title: [''],
		description: [''],
	});

	public constructor(
		private activatedRoute: ActivatedRoute,
		private postsSelectors: PostsSelectors,
		private formBuilder: FormBuilder,
		private router: Router
	) {}

	public ngOnInit(): void {
		console.log('test 0');

		const post$ = this.postsSelectors.posts().pipe(
			combineLatestWith(this.getPostId()),
			map(([posts, postId]) => posts.find(post => post?.id === postId)),
			typedFilter<PostBean>(post => post !== undefined && post !== null)
		);

		console.log('test 1');

		firstValueFrom(post$)
			.then((post: PostBean) => this.onPostLoadSuccess(post))
			.catch(() => this.onPostLoadFail());
	}

	public get descriptionFormControl(): FormControl {
		return this.postForm.controls['description'] as FormControl;
	}

	private getPostId(): Observable<number> {
		return this.activatedRoute.params.pipe(
			map(params => parseInt(params['id'], 10)),
			typedFilter<number>(postId => postId !== undefined)
		);
	}

	private onPostLoadSuccess(post: PostBean): void {
		this.post = post;
		console.log('test 2');
		// this.postForm.setValue({
		// 	title: post.title,
		// 	description: '',
		// });
	}

	private onPostLoadFail(): void {
		console.log('test 3');
		this.router
			.navigateByUrl('/posts')
			.catch(() => console.log('Unable to redirect to posts page.'));
	}
}
