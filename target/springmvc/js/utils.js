
/**
 * 验证email
 * @param email
 * @returns
 */
function regEmail(email){
	var result=email.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/);
	return result == null ? false : true;
}