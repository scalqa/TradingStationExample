# TradingStationExample

This is an example of GUI application developed based on [Scalqa](https://github.com/scalqa/scalqa/). 
The provided code is only for the GUI components (proprietary data model is a library jar). 

The station is currently not used in real trading, so the code is a bit rusty. 
GUI itself is for in-house use and not very intuitive. 
Nevertheless, this application gives a good chance to evaluate functionality, scalability, and ease of use for the API.

To compile/launch the application start SBT in project directory and execute "run". 

# Ideas To Try

The demo contains a stock data feed simulation. "Right-Clicking" "Sim Tape" allows generating as many trading symbols as required, 
testing scalability. But, keep in mind that 10 thousand symbols would produce same feed load as all North American markets, 
and all this information will need to stay in memory taking roughly 3gB (JVM might need more memory assigned).

Once many tickers are generated, "right-clicking" on "Sim Account1", then "Potential", then "Get All", 
will open all account positions for all tickers in the list. This will provide scalability ideas of the GUI itself, 
which can handle lists of virtually unlimited sizes.
