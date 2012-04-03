The files in this folder contain tweets from countries involved in the Arab Spring. The files are organized as tab-separated value(TSV) files and each file contains 2,000 tweets from one country. The tweets are in one of the following languages: English, Arabic, or French, so care should be taken while reading the files.

Following are the fields and their interpretation:
TweetID: unique Tweet id
Text: Text of the tweet
User: Twitter handle of the user who published the tweet
Pubtime: Time of publishing of the tweet. Format: yyyy-mm-dd hh:mm:ss
Lat: latitude (obtained from tweet (see geotag for more info), or by converting location to (lat,lng))
Lng: longitude (obtained from tweet (see geotag for more info), or by converting location to (lat,lng))
Location: Profile location of the user who published the tweet; null, if unavailable
Geotag: True, if the tweet was tagged with a location. False, if the tweet had no location information.
Publish Mode: Name of the Twitter client which was used to publish the tweet.
Country: This field contains the name of the country with which the tweet is associated.







