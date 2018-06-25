import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FormAttributes } from './form-attributes.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FormAttributes>;

@Injectable()
export class FormAttributesService {

    private resourceUrl =  SERVER_API_URL + 'api/form-attributes';

    constructor(private http: HttpClient) { }

    create(formAttributes: FormAttributes): Observable<EntityResponseType> {
        const copy = this.convert(formAttributes);
        return this.http.post<FormAttributes>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(formAttributes: FormAttributes): Observable<EntityResponseType> {
        const copy = this.convert(formAttributes);
        return this.http.put<FormAttributes>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FormAttributes>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FormAttributes[]>> {
        const options = createRequestOption(req);
        return this.http.get<FormAttributes[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FormAttributes[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FormAttributes = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FormAttributes[]>): HttpResponse<FormAttributes[]> {
        const jsonResponse: FormAttributes[] = res.body;
        const body: FormAttributes[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FormAttributes.
     */
    private convertItemFromServer(formAttributes: FormAttributes): FormAttributes {
        const copy: FormAttributes = Object.assign({}, formAttributes);
        return copy;
    }

    /**
     * Convert a FormAttributes to a JSON which can be sent to the server.
     */
    private convert(formAttributes: FormAttributes): FormAttributes {
        const copy: FormAttributes = Object.assign({}, formAttributes);
        return copy;
    }
}
