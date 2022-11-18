import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageBody } from './page-body.component';

describe('PageBodyComponent', () => {
  let component: PageBody;
  let fixture: ComponentFixture<PageBody>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PageBody],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PageBody);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
