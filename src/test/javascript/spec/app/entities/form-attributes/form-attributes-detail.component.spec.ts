/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmoghServerTestModule } from '../../../test.module';
import { FormAttributesDetailComponent } from '../../../../../../main/webapp/app/entities/form-attributes/form-attributes-detail.component';
import { FormAttributesService } from '../../../../../../main/webapp/app/entities/form-attributes/form-attributes.service';
import { FormAttributes } from '../../../../../../main/webapp/app/entities/form-attributes/form-attributes.model';

describe('Component Tests', () => {

    describe('FormAttributes Management Detail Component', () => {
        let comp: FormAttributesDetailComponent;
        let fixture: ComponentFixture<FormAttributesDetailComponent>;
        let service: FormAttributesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [FormAttributesDetailComponent],
                providers: [
                    FormAttributesService
                ]
            })
            .overrideTemplate(FormAttributesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormAttributesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormAttributesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FormAttributes(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.formAttributes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
