/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { findAllFeedBacksByBook } from '../fn/feed-back/find-all-feed-backs-by-book';
import { FindAllFeedBacksByBook$Params } from '../fn/feed-back/find-all-feed-backs-by-book';
import { PageResponseFeedBackResponse } from '../models/page-response-feed-back-response';
import { saveFeedBack } from '../fn/feed-back/save-feed-back';
import { SaveFeedBack$Params } from '../fn/feed-back/save-feed-back';

@Injectable({ providedIn: 'root' })
export class FeedBackService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `saveFeedBack()` */
  static readonly SaveFeedBackPath = '/feedbacks/path';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `saveFeedBack()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveFeedBack$Response(params?: SaveFeedBack$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return saveFeedBack(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `saveFeedBack$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveFeedBack(params?: SaveFeedBack$Params, context?: HttpContext): Observable<number> {
    return this.saveFeedBack$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findAllFeedBacksByBook()` */
  static readonly FindAllFeedBacksByBookPath = '/feedbacks/book/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllFeedBacksByBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllFeedBacksByBook$Response(params: FindAllFeedBacksByBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFeedBackResponse>> {
    return findAllFeedBacksByBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllFeedBacksByBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllFeedBacksByBook(params: FindAllFeedBacksByBook$Params, context?: HttpContext): Observable<PageResponseFeedBackResponse> {
    return this.findAllFeedBacksByBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFeedBackResponse>): PageResponseFeedBackResponse => r.body)
    );
  }

}
