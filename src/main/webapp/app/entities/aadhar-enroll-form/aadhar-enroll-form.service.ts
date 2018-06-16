import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AadharEnrollForm } from './aadhar-enroll-form.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AadharEnrollForm>;

@Injectable()
export class AadharEnrollFormService {

    private resourceUrl =  SERVER_API_URL + 'api/aadhar-enroll-forms';

    constructor(private http: HttpClient) { }

    create(aadharEnrollForm: AadharEnrollForm): Observable<EntityResponseType> {
        const copy = this.convert(aadharEnrollForm);
        return this.http.post<AadharEnrollForm>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(aadharEnrollForm: AadharEnrollForm): Observable<EntityResponseType> {
        const copy = this.convert(aadharEnrollForm);
        return this.http.put<AadharEnrollForm>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AadharEnrollForm>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AadharEnrollForm[]>> {
        const options = createRequestOption(req);
        return this.http.get<AadharEnrollForm[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AadharEnrollForm[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AadharEnrollForm = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AadharEnrollForm[]>): HttpResponse<AadharEnrollForm[]> {
        const jsonResponse: AadharEnrollForm[] = res.body;
        const body: AadharEnrollForm[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AadharEnrollForm.
     */
    private convertItemFromServer(aadharEnrollForm: AadharEnrollForm): AadharEnrollForm {
        const copy: AadharEnrollForm = Object.assign({}, aadharEnrollForm);
        return copy;
    }

    /**
     * Convert a AadharEnrollForm to a JSON which can be sent to the server.
     */
    private convert(aadharEnrollForm: AadharEnrollForm): AadharEnrollForm {
        const copy: AadharEnrollForm = Object.assign({}, aadharEnrollForm);
        return copy;
    }
}
