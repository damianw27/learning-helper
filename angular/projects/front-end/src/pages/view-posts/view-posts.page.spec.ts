import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPostsPage } from './view-posts.page';

describe('PostsComponent', () => {
  let component: ViewPostsPage;
  let fixture: ComponentFixture<ViewPostsPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewPostsPage],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewPostsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
