  setInterval(function () {
            var m = moment();
            var dtStr = "<span>" + m.format("llll") + "</span>";
            $("#dtNow").html(dtStr);
            $("#lblWelcome").html(getGreetingTime(m) + ", " + agent);
        }, 1000);
        function getGreetingTime(m) {
            var g = null; //return g

            if (!m || !m.isValid()) { return; } //if we can't find a valid or filled moment, we return.

            var split_afternoon = 12; //24hr time to split the afternoon
            var split_evening = 18; //24hr time to split the evening
            var currentHour = parseFloat(m.format("HH"));

            if (currentHour >= split_afternoon && currentHour <= split_evening) {
                g = "Afternoon";
            } else if (currentHour >= split_evening) {
                g = "Evening";
            } else {
                g = "Morning";
            }

            return g;
        }