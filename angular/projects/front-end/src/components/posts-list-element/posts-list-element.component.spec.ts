import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsListElementComponent } from './posts-list-element.component';

describe('PostsListElementComponent', () => {
  let component: PostsListElementComponent;
  let fixture: ComponentFixture<PostsListElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostsListElementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostsListElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
