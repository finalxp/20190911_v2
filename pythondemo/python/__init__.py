import speakin_long_vec_sdk
import time
import datetime
import hashlib
import hmac
import base64

GMT_FORMAT = '%a, %d %b %Y %H:%M:%S GMT'
SecretId = "AKID85ejf3jkqmm6jrdpndgKIzZAggUac4urF2e5"
SecretKey = "l58sb1023g1Z9dfrf0f9pz452vrAyc2Piyhyf3l"

def getSimpleSign(source, SecretId, SecretKey):
    if SecretId ==  "" or SecretKey == "":
        return "",""
    dateTime = datetime.datetime.utcnow().strftime(GMT_FORMAT)
    auth = "hmac id=\"" + SecretId + "\", algorithm=\"hmac-sha1\", headers=\"date source\", signature=\""
    signStr = "date: " + dateTime + "\n" + "source: " + source
    sign = hmac.new(SecretKey, signStr, hashlib.sha1).digest()
    sign = base64.b64encode(sign)
    sign = auth + sign + "\""
    return sign, dateTime

if __name__ =="__main__":
    Source = 'test'
    sign, dateTime = getSimpleSign(Source, SecretId, SecretKey)
    headers = {'Authorization': sign, 'Date': dateTime, 'Source': Source}
    api =speakin_long_vec_sdk.LongreleasevApi()
    api.api_client.default_headers=headers

    #result =api.post_long_vecforregister(file1="./test1.wav",file2="./test2.wav",file3="./test3.wav")
    #result =api.post_long_vecforregister(file1="Z:/Test/out.wav",file2="Z:/Test/out2.wav",file3="Z:/Test/out3.wav")
    #print(result)
    
    #result = api.post_long_vecforcompare(file1="Z:/Test/out.wav")
    #print(result)
    
    req= speakin_long_vec_sdk.VoiceprintRawcompareReq()
    req.vec_id="72da515c-92eb-4616-b5aa-9cffe377357c"
    req.cmp_vec_id_list=["5125a77a-54f3-40b3-b8d4-e2f09f398bf6"]
    result = api.post_long_compare(req)
    print(result)
    
    