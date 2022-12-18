import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActionReducer, MetaReducer, StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { AuthEffects } from './auth/auth.effect';
import { authReducer } from './auth/auth.reducer';
import { externalApiReducer } from './external-api/external-api.reducer';
import { postsReducer } from './posts/posts.reducer';
import { questionsReducer } from './questions/questions.reducer';
import { subPostsReducer } from './sub-posts/sub-posts.reducer';
import { PostsEffects } from './posts/posts.effect';
import { SubPostsEffect } from './sub-posts/sub-posts.effect';

export function debug(reducer: ActionReducer<any>): ActionReducer<any> {
	return function (state, action) {
		if (state === undefined || action === undefined) {
			return reducer(state, action);
		}

		console.group(`Action (${action.type})`);
		console.log('State', state);
		console.log('Action', action);
		console.groupEnd();
		return reducer(state, action);
	};
}

const metaReducers: MetaReducer<any>[] = [debug];

@NgModule({
	declarations: [],
	imports: [
		CommonModule,
		StoreModule.forRoot(
			{
				auth: authReducer,
				externalApi: externalApiReducer,
				posts: postsReducer,
				questions: questionsReducer,
				subPosts: subPostsReducer,
			},
			{ metaReducers }
		),
		EffectsModule.forRoot([AuthEffects, PostsEffects, SubPostsEffect]),
	],
})
export class FrontEndStoreModule {}
