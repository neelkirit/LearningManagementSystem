/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmoghServerTestModule } from '../../../test.module';
import { FormDetailComponent } from '../../../../../../main/webapp/app/entities/form/form-detail.component';
import { FormService } from '../../../../../../main/webapp/app/entities/form/form.service';
import { Form } from '../../../../../../main/webapp/app/entities/form/form.model';

describe('Component Tests', () => {

    describe('Form Management Detail Component', () => {
        let comp: FormDetailComponent;
        let fixture: ComponentFixture<FormDetailComponent>;
        let service: FormService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [FormDetailComponent],
                providers: [
                    FormService
                ]
            })
            .overrideTemplate(FormDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Form(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.form).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
