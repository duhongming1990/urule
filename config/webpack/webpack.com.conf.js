let titleFun = function(chunkName,title){
    let titleDef = '';
    if(chunkName.indexOf('index') !==-1){
        return titleDef;
    }else{
        return title + titleDef;
    }
};
module.exports = {
    titleFun:titleFun
};
