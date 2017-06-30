# Page_Scraper
Program get all link and images from website which user will type in console.
Program exports all links(website links and image links) in .txt file and asks user if she/he wants to download images.

I've created class HtmlGeneratos which handles converting website url into html string.
I've also created two class HtmlImageReader for images and HtmlLinkReader for links.
HtmlImageReader's function getImages() finds all <img ..> tag and the find all "src" attribute within it.
User can get all unique imagelinks in list. 
Usage of HtmlLinkReader is pretty much same, except it has getLinks() function instead of getImages() and finds of course <a></a> tags and "href" attributes.
Both classes use regular expressions.
