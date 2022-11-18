import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageSmallBodyComponent } from './page-small-body.component';

describe('PageSmallBodyComponent', () => {
  let component: PageSmallBodyComponent;
  let fixture: ComponentFixture<PageSmallBodyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageSmallBodyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PageSmallBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
