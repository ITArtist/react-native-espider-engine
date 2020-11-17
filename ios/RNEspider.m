#import "RNEspider.h"

@implementation RNEspider

- (dispatch_queue_t)methodQueue
{
    return dispatch_queue_create("com.tudal.tp.ESpider", DISPATCH_QUEUE_SERIAL);
}

RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"WorkFinish", @"WorkStart"];
}
    
    //cancelJob

RCT_REMAP_METHOD(cancelJob, cancelJob: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    if (_worker != nil) {
        [_worker dispose];
        _worker = nil;
    }
}


RCT_REMAP_METHOD(execute, execute: (NSDictionary*)jobInfo
                 resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    NSMutableArray* jobs = [[NSMutableArray alloc] init];
    [jobs addObject:jobInfo];
    
    if (_worker != nil) {
        [_worker dispose];
        _worker = nil;
    }
    
    ESpiderWorker* worker = [[ESpiderWorker alloc] initWithHandler: self];
    [worker runJobs:jobs resolver:resolve rejecter:reject];
    _worker = worker;
}


RCT_REMAP_METHOD(execute2, execute2: (NSArray*)input
                 resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    NSMutableArray* jobs = [[NSMutableArray alloc] initWithArray: input];
    if (_worker != nil) {
        [_worker dispose];
        _worker = nil;
    }
    
    ESpiderWorker* worker = [[ESpiderWorker alloc] initWithHandler: self];
    [worker runJobs:jobs resolver:resolve rejecter:reject];
    _worker = worker;
}

RCT_REMAP_METHOD(getS4AccountsByLogin, getS4AccountsByLogin: (NSString*)s4code
                 username: (NSString*)username password: (NSString*)password
                 resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    NSMutableArray* jobs = [[NSMutableArray alloc] init];
    
    NSMutableDictionary *module = [NSMutableDictionary dictionary];
    [module setObject:@"KR"      forKey:@"country"];
    [module setObject:@"ST"      forKey:@"organization"];
    [module setObject:s4code     forKey:@"suborganization"];
    [module setObject:@"311040"  forKey:@"code"];
    [module setObject:@"전계좌조회" forKey:@"module_disply_name"];
    
    NSMutableDictionary *paramLogin = [NSMutableDictionary dictionary];
    [paramLogin setObject:username forKey:@"reqUserId"];       // 아이디
    [paramLogin setObject:password forKey:@"reqUserPass"]; // 비밀번호
    
    NSMutableDictionary *paramInfo     = [NSMutableDictionary dictionary];
    [paramInfo setObject:@""    forKey:@"reqAccount"]; //계좌번호
    [paramInfo setObject:@""    forKey:@"reqAccountPass"];    //계좌 비밀번호
    
    NSMutableDictionary *jobInfo = [NSMutableDictionary dictionary];
    [jobInfo setObject:@"_"         forKey:@"_KEY"];
    [jobInfo setObject:module       forKey:ENGINE_JOB_MODULE_KEY];
    [jobInfo setObject:paramLogin   forKey:ENGINE_JOB_PARAM_LOGIN_KEY];
    [jobInfo setObject:paramInfo    forKey:ENGINE_JOB_PARAM_INFO_KEY];
    [jobInfo setObject:@{}          forKey:ENGINE_JOB_PARAMEXT_INFO_KEY];
    
    [jobs addObject:jobInfo];
    
    if (_worker != nil) {
        [_worker dispose];
        _worker = nil;
    }
    
    ESpiderWorker* worker = [[ESpiderWorker alloc] init];
    [worker runJobs:jobs resolver:resolve rejecter:reject];
    _worker = worker;
}


RCT_REMAP_METHOD(getS4AccountsByCert, getS4AccountsByCert: (NSString*)s4code
                 certPath: (NSString*)certPath keyPath: (NSString*)keyPath
                 username: (NSString*)username password: (NSString*)password
                 resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    NSMutableArray* jobs = [[NSMutableArray alloc] init];
    
    NSMutableDictionary *module = [NSMutableDictionary dictionary];
    [module setObject:@"KR"      forKey:@"country"];
    [module setObject:@"ST"      forKey:@"organization"];
    [module setObject:s4code     forKey:@"suborganization"];
    [module setObject:@"310040"  forKey:@"code"];
    [module setObject:@"전계좌조회" forKey:@"module_disply_name"];
    
    NSMutableDictionary *paramLogin = [NSMutableDictionary dictionary];
    [paramLogin setObject:certPath  forKey:@"reqCertFile"];
    [paramLogin setObject:keyPath   forKey:@"reqKeyFile"];
    [paramLogin setObject:password  forKey:@"reqCertPass"];
    [paramLogin setObject:username  forKey:@"reqUserId"];
    
    NSMutableDictionary *paramInfo     = [NSMutableDictionary dictionary];
    [paramInfo setObject:@""    forKey:@"reqAccount"];
    [paramInfo setObject:@""    forKey:@"reqAccountPass"];
    
    NSMutableDictionary *jobInfo = [NSMutableDictionary dictionary];
    [jobInfo setObject:@"_"         forKey:@"_KEY"];
    [jobInfo setObject:module       forKey:ENGINE_JOB_MODULE_KEY];
    [jobInfo setObject:paramLogin   forKey:ENGINE_JOB_PARAM_LOGIN_KEY];
    [jobInfo setObject:paramInfo    forKey:ENGINE_JOB_PARAM_INFO_KEY];
    [jobInfo setObject:@{}          forKey:ENGINE_JOB_PARAMEXT_INFO_KEY];
    
    [jobs addObject:jobInfo];
    
    if (_worker != nil) {
        [_worker dispose];
        _worker = nil;
    }
    
    ESpiderWorker* worker = [[ESpiderWorker alloc] init];
    [worker runJobs:jobs resolver:resolve rejecter:reject];
    _worker = worker;
}


RCT_REMAP_METHOD(oneclickByCert, oneclickByCert: (NSArray*)rows
                 certPath: (NSString*)certPath keyPath: (NSString*)keyPath password: (NSString*)password
                 resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    NSMutableArray* jobs = [[NSMutableArray alloc] init];
    for (NSDictionary* row in rows) {
        NSString* s4code = [row objectForKey:@"key"];
        
        NSMutableDictionary *module = [NSMutableDictionary dictionary];
        [module setObject:@"KR"      forKey:@"country"];
        [module setObject:@"ST"      forKey:@"organization"];
        [module setObject:s4code     forKey:@"suborganization"];
        [module setObject:@"310040"  forKey:@"code"];
        [module setObject:@"전계좌조회" forKey:@"module_disply_name"];
        
        NSMutableDictionary *paramLogin = [NSMutableDictionary dictionary];
        [paramLogin setObject:certPath  forKey:@"reqCertFile"];
        [paramLogin setObject:keyPath   forKey:@"reqKeyFile"];
        [paramLogin setObject:password  forKey:@"reqCertPass"];
        
        NSMutableDictionary *paramInfo     = [NSMutableDictionary dictionary];
        [paramInfo setObject:@""    forKey:@"reqAccount"];
        [paramInfo setObject:@""    forKey:@"reqAccountPass"];
        
        NSMutableDictionary *jobInfo = [NSMutableDictionary dictionary];
        [jobInfo setObject:@"_"         forKey:@"_KEY"];
        [jobInfo setObject:module       forKey:ENGINE_JOB_MODULE_KEY];
        [jobInfo setObject:paramLogin   forKey:ENGINE_JOB_PARAM_LOGIN_KEY];
        [jobInfo setObject:paramInfo    forKey:ENGINE_JOB_PARAM_INFO_KEY];
        [jobInfo setObject:@{}          forKey:ENGINE_JOB_PARAMEXT_INFO_KEY];
        
        [jobs addObject:jobInfo];
    }
    
    if (_worker != nil) {
        [_worker dispose];
        _worker = nil;
    }
    
    ESpiderWorker* worker = [[ESpiderWorker alloc] initWithHandler: self];
    [worker runJobs:jobs resolver:resolve rejecter:reject];
    _worker = worker;
}


RCT_REMAP_METHOD(getStocksByJobs, getStocksByJobs: (NSArray*)rows resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    NSMutableArray* jobs = [[NSMutableArray alloc] init];
    for (NSDictionary* row in rows) {
        
        NSString* method = [[row objectForKey:@"method"] isEqualToString:@"username"] ? @"311010" : @"310010";
        
        NSString* s4code = [row objectForKey:@"s4code"];
        NSString* username = [row objectForKey:@"id"];
        NSString* password = [row objectForKey:@"pw"];
        
        NSString* reqCertFile = [row objectForKey:@"certPath"];
        NSString* reqKeyFile  = [row objectForKey:@"keyPath"];
        NSString* reqCertPass = [row objectForKey:@"certPass"];
        
        NSString* account = [row objectForKey:@"account"];
        NSString* accountPass = [row objectForKey:@"accountPass"];
        
        NSMutableDictionary *module = [NSMutableDictionary dictionary];
        [module setObject:@"KR"         forKey:@"country"];
        [module setObject:@"ST"         forKey:@"organization"];
        [module setObject:s4code        forKey:@"suborganization"];
        [module setObject:method        forKey:@"code"];
        [module setObject:@"details"    forKey:@"module_disply_name"];
        
        NSMutableDictionary *paramLogin = [NSMutableDictionary dictionary];
        [paramLogin setObject:username      forKey:@"reqUserId"];       // 아이디
        [paramLogin setObject:password      forKey:@"reqUserPass"]; // 비밀번호
        [paramLogin setObject:reqCertFile   forKey:@"reqCertFile"]; // 비밀번호
        [paramLogin setObject:reqKeyFile    forKey:@"reqKeyFile"]; // 비밀번호
        [paramLogin setObject:reqCertPass   forKey:@"reqCertPass"];
        
        NSMutableDictionary *paramInfo     = [NSMutableDictionary dictionary];
        [paramInfo setObject:account        forKey:@"reqAccount"]; //계좌번호
        [paramInfo setObject:accountPass    forKey:@"reqAccountPass"];    //계좌 비밀번호
        [paramInfo setObject:@"0"           forKey:@"reqSearchGbn"];
        
        NSMutableDictionary *jobInfo = [NSMutableDictionary dictionary];
        [jobInfo setObject:[NSString stringWithFormat:@"_%@%@", s4code, account]         forKey:@"_KEY"];
        [jobInfo setObject:module       forKey:ENGINE_JOB_MODULE_KEY];
        [jobInfo setObject:paramLogin   forKey:ENGINE_JOB_PARAM_LOGIN_KEY];
        [jobInfo setObject:paramInfo    forKey:ENGINE_JOB_PARAM_INFO_KEY];
        [jobInfo setObject:@{}          forKey:ENGINE_JOB_PARAMEXT_INFO_KEY];
        
        [jobs addObject:jobInfo];
    }
    
    if (_worker != nil) {
        [_worker dispose];
        _worker = nil;
    }
    
    ESpiderWorker* worker = [[ESpiderWorker alloc] initWithHandler: self];
    [worker runJobs:jobs resolver:resolve rejecter:reject];
    _worker = worker;

}


RCT_REMAP_METHOD(validPassByLogin, validPassByLogin: (NSString*)s4code
                 account: (NSString*)account accountPass: (NSString*) accountPass
                 username: (NSString*)username password: (NSString*)password
                 resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    NSMutableArray* jobs = [[NSMutableArray alloc] init];
    
    NSMutableDictionary *module = [NSMutableDictionary dictionary];
    [module setObject:@"KR"         forKey:@"country"];
    [module setObject:@"ST"         forKey:@"organization"];
    [module setObject:s4code        forKey:@"suborganization"];
    [module setObject:@"311010"     forKey:@"code"];
    [module setObject:@"details"    forKey:@"module_disply_name"];
    
    NSMutableDictionary *paramLogin = [NSMutableDictionary dictionary];
    [paramLogin setObject:username forKey:@"reqUserId"];       // 아이디
    [paramLogin setObject:password forKey:@"reqUserPass"]; // 비밀번호
    
    NSMutableDictionary *paramInfo     = [NSMutableDictionary dictionary];
    [paramInfo setObject:account        forKey:@"reqAccount"]; //계좌번호
    [paramInfo setObject:accountPass    forKey:@"reqAccountPass"];    //계좌 비밀번호
    [paramInfo setObject:@"1"           forKey:@"reqSearchGbn"];
    
    NSMutableDictionary *jobInfo = [NSMutableDictionary dictionary];
    [jobInfo setObject:@"_"         forKey:@"_KEY"];
    [jobInfo setObject:module       forKey:ENGINE_JOB_MODULE_KEY];
    [jobInfo setObject:paramLogin   forKey:ENGINE_JOB_PARAM_LOGIN_KEY];
    [jobInfo setObject:paramInfo    forKey:ENGINE_JOB_PARAM_INFO_KEY];
    [jobInfo setObject:@{}          forKey:ENGINE_JOB_PARAMEXT_INFO_KEY];
    
    [jobs addObject:jobInfo];
    
    ESpiderWorker* worker = [[ESpiderWorker alloc] init];
    [worker runJobs:jobs resolver:resolve rejecter:reject];
    _worker = worker;
}

RCT_REMAP_METHOD(validPassByCert, getDetailsByCert: (NSString*)s4code
                 account: (NSString*)account accountPass: (NSString*) accountPass
                 certPath: (NSString*)certPath keyPath: (NSString*)keyPath
                 username: (NSString*)username password: (NSString*)password
                 resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    NSMutableArray* jobs = [[NSMutableArray alloc] init];
    
    NSMutableDictionary *module = [NSMutableDictionary dictionary];
    [module setObject:@"KR"      forKey:@"country"];
    [module setObject:@"ST"      forKey:@"organization"];
    [module setObject:s4code     forKey:@"suborganization"];
    [module setObject:@"310010"  forKey:@"code"];
    [module setObject:@"details" forKey:@"module_disply_name"];
    
    NSMutableDictionary *paramLogin = [NSMutableDictionary dictionary];
    [paramLogin setObject:certPath  forKey:@"reqCertFile"];
    [paramLogin setObject:keyPath   forKey:@"reqKeyFile"];
    [paramLogin setObject:password  forKey:@"reqCertPass"];
    [paramLogin setObject:username  forKey:@"reqUserId"];
    
    NSMutableDictionary *paramInfo     = [NSMutableDictionary dictionary];
    [paramInfo setObject:account        forKey:@"reqAccount"];
    [paramInfo setObject:accountPass    forKey:@"reqAccountPass"];
    [paramInfo setObject:@"1"           forKey:@"reqSearchGbn"];
    
    NSMutableDictionary *jobInfo = [NSMutableDictionary dictionary];
    [jobInfo setObject:@"_"         forKey:@"_KEY"];
    [jobInfo setObject:module       forKey:ENGINE_JOB_MODULE_KEY];
    [jobInfo setObject:paramLogin   forKey:ENGINE_JOB_PARAM_LOGIN_KEY];
    [jobInfo setObject:paramInfo    forKey:ENGINE_JOB_PARAM_INFO_KEY];
    [jobInfo setObject:@{}          forKey:ENGINE_JOB_PARAMEXT_INFO_KEY];
    
    [jobs addObject:jobInfo];
    
    ESpiderWorker* worker = [[ESpiderWorker alloc] init];
    [worker runJobs:jobs resolver:resolve rejecter:reject];
    _worker = worker;
    
//    [self sendEventWithName:@"Logs" body: @{}];
}

@end
  
