import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Pagination } from './pagination.component';

describe('PaginationComponent', () => {
  let component: Pagination;
  let fixture: ComponentFixture<Pagination>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Pagination],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Pagination);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
