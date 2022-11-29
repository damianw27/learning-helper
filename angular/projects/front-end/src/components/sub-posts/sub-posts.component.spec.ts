import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubPosts } from './sub-posts.component';

describe('LessonListComponent', () => {
  let component: SubPosts;
  let fixture: ComponentFixture<SubPosts>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubPosts],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubPosts);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
