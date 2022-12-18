import { Injectable } from '@angular/core';
import { FrontEndState } from '../front-end.state';
import { createSelector, Store } from '@ngrx/store';
import { distinctUntilChanged } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class QuestionsSelectorService {
	private readonly selectQuestionsState = (state: FrontEndState) =>
		state.questions;

	private readonly selectQuestions = (subPostId: number) =>
		createSelector(this.selectQuestionsState, state =>
			Object.values(state.entities).filter(
				question => question?.subPostId === subPostId
			)
		);

	public constructor(private readonly store: Store<FrontEndState>) {}

	public readonly questions = (subPostId: number) =>
		this.store
			.select(state => this.selectQuestions(subPostId)(state))
			.pipe(distinctUntilChanged());
}
