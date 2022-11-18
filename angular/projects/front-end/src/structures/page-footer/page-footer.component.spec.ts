import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageFooter } from './page-footer.component';

describe('PageFooterComponent', () => {
  let component: PageFooter;
  let fixture: ComponentFixture<PageFooter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PageFooter],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PageFooter);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
