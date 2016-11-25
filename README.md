Amend
==========

amend is a cloud-based service that provides an end-to-end image management solution including uploads, storage, manipulations, optimizations and delivery.

With amend you can easily upload images to the cloud, automatically perform smart image manipulations without installing any complex software. All your images are then seamlessly delivered through a fast CDN, optimized and using industry best practices. amend offers comprehensive APIs and administration capabilities and is easy to integrate with new and existing web and mobile applications.

amend offers comprehensive APIs and administration capabilities and is easy to integrate with any web application, existing or new.

amend provides URL and HTTP based APIs that can be easily integrated with any Mobile development framework. 

For projects based on Android Framework, amend provides a library for simplifying the integration even further.

## Getting started guide
[Getting started guide for Android]

## Setup ######################################################################

amend Android library is available at [http://amendcloud.com/library/amend_android.zip](http://amendcloud.com/library/amend_android.zip)

### Manual setup

1. Go to [http://amendcloud.com/library/android.zip](http://amendcloud.com/library/amend_android.zip) and download android library.
2. Library is a ZIP archive that could be extracted using any unzip tool.
3. Import library folder as a module in your android project.

## Try it right away

Sign up for a [free account](http://developer.amendcloud.com/Register) so you can try out image transformations and seamless image delivery through CDN.

*Note: Replace `demo` in all the following examples with your amend's `amend name`.*  

Accessing an uploaded image with the `pc` public ID through a CDN:

    http://amend.cloud/demo/image/pc.jpg

![Sample](http://amend.cloud/demo/image/w_300/pc.jpg "Sample")

Generating a 150x100 version of the `pc` image and downloading it through a CDN:

    http://amend.cloud/demo/image/w_150,h_100,fit_fill/pc.jpg

![Sample 150x100](http://amend.cloud/demo/image/w_150,h_100,fit_fill/pc.jpg "Sample 150x100")

Converting to a 150x100 PNG with rounded corners of 25 pixels: 

    http://amend.cloud/demo/image/w_150,h_100,fit_fill/r_25/pc.jpg

![Sample 150x150 Rounded PNG](http://amend.cloud/demo/image/w_150,h_100,fit_fill/r_25/pc.jpg "Sample 150x150 Rounded PNG")

For plenty more transformation options, see our [image transformations documentation](http://amend.com/documentation/image_transformations).
 
## Usage

### Configuration

Each request for building a URL of a remote cloud resource must have the `amend_name` parameter set. 
Each request to our secure APIs (e.g., image uploads) must have the `access_key` and `access_secret` parameters set. 


Setting the `amend_name`, `access_key` and `access_secret` parameters can be done either directly in each call to a amend  method, 
by when initializing the amend object.

The main entry point of the library is the Amend object.

	Amend.setAmendName("my_amend_name");
	Amend.setCredentials("my_access_key", "my_access_secret");


### Upload

Assuming you have your amend configuration parameters defined (`amend_name`, `access_key`, `access_secret`), uploading to amend is very simple.
    
The following example uploads a file to the cloud: 

	 Amend.with(this).upload(reqCode, file, new AmendListener() {
	 
		@Override
		public void onSuccess(int statusCode, int reqCode, String imageName) {
			
		}

		@Override
		public void onError(int statusCode, int reqCode, Exception e) {

		}
	});
   
you can get Image Name return by amend upload api in onSuccess method

	@Override
	public void onSuccess(int statusCode, int reqCode, String imageName) {
		string myImage = imageName;
	}
	

You can also specify your own Image Name:    
    
    Amend.with(this).upload(reqCode, file, "myFileName", new AmendListener() {
	 
		@Override
		public void onSuccess(int statusCode, int reqCode, String imageName) {
			
		}

		@Override
		public void onError(int statusCode, int reqCode, Exception e) {

		}
	});

	
### Embedding and transforming images

Any image uploaded to amend can be transformed and embedded using powerful view helper methods:

The following example generates the url for accessing an uploaded `pc` image while transforming it to fill a 100x150 rectangle and set on ImageView img

	AmendOptions amendOptions = new AmendOptions().transform(new Transform().width(100).height(150).fit(Amend.FIT_FILL));
    Amend.with(context).load('pc', amendOptions).into(img);

Another example, emedding a smaller version of an uploaded image while generating a 90x90 face detection based thumbnail: 

	AmendOptions amendOptions = new AmendOptions().transform(new Transform().width(90).height(90).fit(Amend.FIT_FACE));
    Amend.with(context).load('woman', amendOptions).into(img);
	  
  
## Additional resources

Additional resources are available at:

* [Website](http://amendcloud.com)
* [Documentation](http://amendcloud.com/documentation.html)

## Support

Contact us at [support@amendcloud.com](mailto:support@amendcloud.com)

Or via Twitter: [@amend](https://twitter.com/#!/amendcloud)