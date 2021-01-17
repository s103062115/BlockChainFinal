package food3;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class Food3 extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50600436106100415760003560e01c80631b8ed21b146100465780639c8be847146101c8578063cf087b2c146102f5575b600080fd5b6101c6600480360360a081101561005c57600080fd5b81019080803590602001909291908035906020019064010000000081111561008357600080fd5b82018360208201111561009557600080fd5b803590602001918460018302840111640100000000831117156100b757600080fd5b9091929391929390803590602001906401000000008111156100d857600080fd5b8201836020820111156100ea57600080fd5b8035906020019184600183028401116401000000008311171561010c57600080fd5b90919293919293908035906020019064010000000081111561012d57600080fd5b82018360208201111561013f57600080fd5b8035906020019184600183028401116401000000008311171561016157600080fd5b90919293919293908035906020019064010000000081111561018257600080fd5b82018360208201111561019457600080fd5b803590602001918460018302840111640100000000831117156101b657600080fd5b9091929391929390505050610451565b005b6102f3600480360360808110156101de57600080fd5b81019080803590602001909291908035906020019064010000000081111561020557600080fd5b82018360208201111561021757600080fd5b8035906020019184600183028401116401000000008311171561023957600080fd5b90919293919293908035906020019064010000000081111561025a57600080fd5b82018360208201111561026c57600080fd5b8035906020019184600183028401116401000000008311171561028e57600080fd5b9091929391929390803590602001906401000000008111156102af57600080fd5b8201836020820111156102c157600080fd5b803590602001918460018302840111640100000000831117156102e357600080fd5b90919293919293905050506105f3565b005b61044f6004803603606081101561030b57600080fd5b81019080803590602001909291908035906020019064010000000081111561033257600080fd5b82018360208201111561034457600080fd5b8035906020019184600183028401116401000000008311171561036657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156103c957600080fd5b8201836020820111156103db57600080fd5b803590602001918460018302840111640100000000831117156103fd57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929050505061075f565b005b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610512576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f43616c6c6572206973206e6f74206f776e65720000000000000000000000000081525060200191505060405180910390fd5b81816040518083838082843780830192505050925050506040518091039020888860405180838380828437808301925050509250505060405180910390208a7f5e000335c64f3ab7aa9d64992eb53606b394bf14b8e1994875ab8b2525855e9e60405160405180910390a483836040518083838082843780830192505050925050506040518091039020868660405180838380828437808301925050509250505060405180910390208a7fffa76c5ac909805a1d9509c1dba74bf959f979149dd4e9dcea3d99c033b5d53160405160405180910390a4505050505050505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146106b4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f43616c6c6572206973206e6f74206f776e65720000000000000000000000000081525060200191505060405180910390fd5b8181604051808383808284378083019250505092505050604051809103902084846040518083838082843780830192505050925050506040518091039020887f37eb6fd92b9911b83a4c49729ff496687bcbbdb623a777de2f5b7bb87246d544898960405180806020018281038252848482818152602001925080828437600081840152601f19601f820116905080830192505050935050505060405180910390a450505050505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610820576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526013815260200180";

    public static final String FUNC_FOODLOG = "FoodLog";

    public static final String FUNC_FOODLOGIMAGE = "FoodLogImage";

    public static final String FUNC_FOODLOGSECTION = "FoodLogSection";

    public static final Event FOODCONTENT_EVENT = new Event("FoodContent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event FOODIMAGE_EVENT = new Event("FoodImage",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event FOODIMAGEREPLACE_EVENT = new Event("FoodImageReplace",
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event FOODITEM_EVENT = new Event("FoodItem",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event FOODSECTION_EVENT = new Event("FoodSection",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    @Deprecated
    protected Food3(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Food3(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Food3(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Food3(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<FoodContentEventResponse> getFoodContentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FOODCONTENT_EVENT, transactionReceipt);
        ArrayList<FoodContentEventResponse> responses = new ArrayList<FoodContentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FoodContentEventResponse typedResponse = new FoodContentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.logno = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.logname = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.logorg = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FoodContentEventResponse> foodContentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FoodContentEventResponse>() {
            @Override
            public FoodContentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FOODCONTENT_EVENT, log);
                FoodContentEventResponse typedResponse = new FoodContentEventResponse();
                typedResponse.log = log;
                typedResponse.logno = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.logname = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.logorg = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FoodContentEventResponse> foodContentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FOODCONTENT_EVENT));
        return foodContentEventFlowable(filter);
    }

    public List<FoodImageEventResponse> getFoodImageEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FOODIMAGE_EVENT, transactionReceipt);
        ArrayList<FoodImageEventResponse> responses = new ArrayList<FoodImageEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FoodImageEventResponse typedResponse = new FoodImageEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.logno = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.url = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.filehash = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FoodImageEventResponse> foodImageEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FoodImageEventResponse>() {
            @Override
            public FoodImageEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FOODIMAGE_EVENT, log);
                FoodImageEventResponse typedResponse = new FoodImageEventResponse();
                typedResponse.log = log;
                typedResponse.logno = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.url = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.filehash = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FoodImageEventResponse> foodImageEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FOODIMAGE_EVENT));
        return foodImageEventFlowable(filter);
    }

    public List<FoodImageReplaceEventResponse> getFoodImageReplaceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FOODIMAGEREPLACE_EVENT, transactionReceipt);
        ArrayList<FoodImageReplaceEventResponse> responses = new ArrayList<FoodImageReplaceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FoodImageReplaceEventResponse typedResponse = new FoodImageReplaceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.url = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.oldhash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newhash = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FoodImageReplaceEventResponse> foodImageReplaceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FoodImageReplaceEventResponse>() {
            @Override
            public FoodImageReplaceEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FOODIMAGEREPLACE_EVENT, log);
                FoodImageReplaceEventResponse typedResponse = new FoodImageReplaceEventResponse();
                typedResponse.log = log;
                typedResponse.url = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.oldhash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.newhash = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FoodImageReplaceEventResponse> foodImageReplaceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FOODIMAGEREPLACE_EVENT));
        return foodImageReplaceEventFlowable(filter);
    }

    public List<FoodItemEventResponse> getFoodItemEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FOODITEM_EVENT, transactionReceipt);
        ArrayList<FoodItemEventResponse> responses = new ArrayList<FoodItemEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FoodItemEventResponse typedResponse = new FoodItemEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.logno = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.loghash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.logdate = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FoodItemEventResponse> foodItemEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FoodItemEventResponse>() {
            @Override
            public FoodItemEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FOODITEM_EVENT, log);
                FoodItemEventResponse typedResponse = new FoodItemEventResponse();
                typedResponse.log = log;
                typedResponse.logno = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.loghash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.logdate = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FoodItemEventResponse> foodItemEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FOODITEM_EVENT));
        return foodItemEventFlowable(filter);
    }

    public List<FoodSectionEventResponse> getFoodSectionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FOODSECTION_EVENT, transactionReceipt);
        ArrayList<FoodSectionEventResponse> responses = new ArrayList<FoodSectionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FoodSectionEventResponse typedResponse = new FoodSectionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.logno = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.begin = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.end = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.title = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FoodSectionEventResponse> foodSectionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FoodSectionEventResponse>() {
            @Override
            public FoodSectionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FOODSECTION_EVENT, log);
                FoodSectionEventResponse typedResponse = new FoodSectionEventResponse();
                typedResponse.log = log;
                typedResponse.logno = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.begin = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.end = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.title = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FoodSectionEventResponse> foodSectionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FOODSECTION_EVENT));
        return foodSectionEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> FoodLog(BigInteger logno, String loghash, String logname, String logorg, String logdate) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FOODLOG,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(logno),
                        new org.web3j.abi.datatypes.Utf8String(loghash),
                        new org.web3j.abi.datatypes.Utf8String(logname),
                        new org.web3j.abi.datatypes.Utf8String(logorg),
                        new org.web3j.abi.datatypes.Utf8String(logdate)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> FoodLogImage(BigInteger logno, String url, String filehash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FOODLOGIMAGE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(logno),
                        new org.web3j.abi.datatypes.Utf8String(url),
                        new org.web3j.abi.datatypes.Utf8String(filehash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> FoodLogSection(BigInteger logno, String title, String begin, String end) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FOODLOGSECTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(logno),
                        new org.web3j.abi.datatypes.Utf8String(title),
                        new org.web3j.abi.datatypes.Utf8String(begin),
                        new org.web3j.abi.datatypes.Utf8String(end)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Food3 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Food3(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Food3 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Food3(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Food3 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Food3(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Food3 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Food3(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Food3> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Food3.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Food3> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Food3.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Food3> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Food3.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Food3> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Food3.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class FoodContentEventResponse extends BaseEventResponse {
        public BigInteger logno;

        public byte[] logname;

        public byte[] logorg;
    }

    public static class FoodImageEventResponse extends BaseEventResponse {
        public BigInteger logno;

        public byte[] url;

        public byte[] filehash;
    }

    public static class FoodImageReplaceEventResponse extends BaseEventResponse {
        public byte[] url;

        public byte[] oldhash;

        public byte[] newhash;
    }

    public static class FoodItemEventResponse extends BaseEventResponse {
        public BigInteger logno;

        public byte[] loghash;

        public byte[] logdate;
    }

    public static class FoodSectionEventResponse extends BaseEventResponse {
        public BigInteger logno;

        public byte[] begin;

        public byte[] end;

        public String title;
    }
}
