#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'deezer_playback'
  s.version          = '0.0.1'
  s.summary          = 'Deezer All In One Plugin.'
  s.description      = <<-DESC
A Deezer plugin for flutter.
                       DESC
  s.homepage         = 'https://github.com/naru-inc/deezer-playback/'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'NARU Entreprise' => 'naru-inc@gmail.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'

  s.ios.deployment_target = '8.0'
end

