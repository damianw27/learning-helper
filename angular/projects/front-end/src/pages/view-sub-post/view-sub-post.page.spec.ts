import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSubPostPage } from './view-sub-post.page';

describe('ViewSubPostComponent', () => {
  let component: ViewSubPostPage;
  let fixture: ComponentFixture<ViewSubPostPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewSubPostPage],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewSubPostPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
