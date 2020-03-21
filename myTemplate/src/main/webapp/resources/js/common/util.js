/**
 * jQuery 확장
 */
(function($) {
    $.extend($.fn, {
        populate: function(jsonObj) {
            var $form = $(this);
            $form[0].reset();
            $form.find("textarea").html("");

            $.each(jsonObj, function(key, value) {
                $form.find("input[id=" + key + "], select[id=" + key + "]").not(":radio").val(value);
                $form.find("textarea[id=" + key + "]").html(value);
                if ($form.find("input[id=" + key + "]:radio").length > 0) {
                    var radioList = $form.find("input[id=" + key + "]:radio");
                    $.each(radioList, function(n, radio) {
                        if ($(radio).val() == value) {
                            $(radio).prop("checked", true).change();
                        }
                    });
                }
            });
        },
        serializeForm: function() {
            var result = {};
            var $form = $(this);
            var array = $form.serializeArray();
            $.each(array, function(n, obj) {
                // 배열인 경우
                var key = obj["name"];
                var val = obj["value"];
                if (result[key]) {
                    var tmpObj = result[key];
                    if ($.type(result[key]) != "array") {
                        result[key] = [];
                        result[key].push(tmpObj);
                    }
                    result[key].push(val);
                } else {
                    result[key] = val;
                }
            });
            return result;
        }
    });
})(jQuery);

/**
 * Undefined 체크
 *
 * @param obj
 * @returns {Blooean}
 */
function checkUndefined(obj) {
    return obj === void 0;
};

/**
 * Undefined To ""
 *
 * @param obj
 * @returns {obj or ""}
 */
function convertUndefinedToString(obj, rtn) {
	if(checkUndefined(obj)){
		if(checkUndefined(rtn)){
			return "";
		} else {
			return rtn;
		}
	} else {
		return obj;
	}
}