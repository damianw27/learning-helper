import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageTitle } from './page-title.component';

describe('PageTitleComponent', () => {
  let component: PageTitle;
  let fixture: ComponentFixture<PageTitle>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PageTitle],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PageTitle);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
