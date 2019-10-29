import {DeviceEventEmitter, NativeEventEmitter, NativeModules, Platform} from 'react-native';

const { RNEspider } = NativeModules;

class ESpiderBridge {

    listeners = [];

    constructor() {

        if (Platform.OS == 'ios') {
            const eventEmitter = new NativeEventEmitter(RNEspider);
            console.log(eventEmitter, this);
            eventEmitter.addListener('WorkStart', (props) => this.onWorkStart(props));
            eventEmitter.addListener('WorkFinish', (props) => this.onWorkFinish(props));
        }
        else {
            DeviceEventEmitter.addListener('WorkStart', (props) => this.onWorkStart(props));
            DeviceEventEmitter.addListener('WorkFinish', (props) => this.onWorkFinish(props));
        }

        this.addListener = this.addListener.bind(this);
        this.removeListener = this.removeListener.bind(this);
    }

    addListener(l) {
        this.listeners.push(l);
    }

    removeListener(l) {
        if (this.listeners.indexOf(l) >= 0) {
            this.listeners.splice(this.listeners.indexOf(l), 1);
        }
    }

    onWorkStart(param) {
        console.log("onWorkStart");
        let data = param;
        if (typeof(data.json) == "string") {
            data = JSON.parse(param.json);
        }
        this.listeners.map((fn) => { fn("start", data); });
    }

    onWorkFinish(data) {
        console.log(data);
        this.listeners.map((fn) => { fn("finish", data); });
    }

    hasExecute() {
        return !!RNEspider.execute;
    }

    hasExecute2() {
        return !!RNEspider.execute2;
    }

    execute(data) {
        return RNEspider.execute(data);
    }

    execute2(data) {
        return RNEspider.execute2(data);
    }

    getS4AccountsByLogin(s4code, username, password) {
        return RNEspider.getS4AccountsByLogin(s4code, username, password);
    }

    getS4AccountsByCert(s4code, certPath, keyPath, username, password) {
        return RNEspider.getS4AccountsByCert(s4code, certPath, keyPath, username, password);
    }

    oneclickByCert(rows, certPath, keyPath, password) {
        return RNEspider.oneclickByCert(rows, certPath, keyPath, password);
    }

    getStocksByJobs(rows) {
        return RNEspider.getStocksByJobs(rows);
    }

    validPassByLogin(s4code, account, accountPass, username, password) {
        return RNEspider.validPassByLogin(s4code, account, accountPass, username, password);
    }

    validPassByCert(s4code, account, accountPass, certPath, keyPath, username, password) {
        return RNEspider.validPassByCert(s4code, account, accountPass, certPath, keyPath, username, password);
    }

    cancelJob() {
        return RNEspider.cancelJob();
    }

}

const bridge = new ESpiderBridge();
export default bridge;
