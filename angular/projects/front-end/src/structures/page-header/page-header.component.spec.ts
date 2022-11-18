import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageHeader } from './page-header.component';

describe('PageHeaderComponent', () => {
  let component: PageHeader;
  let fixture: ComponentFixture<PageHeader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PageHeader],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PageHeader);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
