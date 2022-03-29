import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCartPageComponent } from './product-cart-page.component';

describe('ProductCartPageComponent', () => {
  let component: ProductCartPageComponent;
  let fixture: ComponentFixture<ProductCartPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductCartPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductCartPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
