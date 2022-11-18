import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageSmallBody } from './page-small-body.component';

describe('PageSmallBodyComponent', () => {
  let component: PageSmallBody;
  let fixture: ComponentFixture<PageSmallBody>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PageSmallBody],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PageSmallBody);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
