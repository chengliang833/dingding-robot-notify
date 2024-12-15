function repair (i, len = 2) {
    if (i >= 0 && i <= 9) {
        return len == 3 ? "00" : "0" + i;
    } else if(i < 99){
        return len == 3 ? "0"+i : i;
    }else{
        return i;
    }
};

function formatDateTime(date, format = 'yyyy-MM-dd hh:mm:ss') {
    var year = date.getFullYear(),
        month = repair(date.getMonth() + 1),//月份是从0开始的
        day = repair(date.getDate()),
        hour = repair(date.getHours()),
        min = repair(date.getMinutes()),
        seconds = repair(date.getSeconds()),
        milliseconds = repair(date.getMilliseconds(), 3);//,
        // quarter = Math.floor((date.getMonth() + 3) / 3), // 季度
        // a = date.getHours() < 12 ? '上午' : '下午', // 上午/下午
        // A = date.getHours() < 12 ? 'AM' : 'PM'; // AM/PM
    var formatedDate = format.replace(/yyyy/g, year)
        .replace(/MM/g, month)
        .replace(/dd/g, day)
        .replace(/hh/g, hour)
        .replace(/mm/g, min)
        .replace(/ss/g, seconds)
        .replace(/SSS/g, milliseconds);
    // console.log(formatedDate);
    return formatedDate;
}