# cli-list
> Break CLI lists into arrays

## Installation
```shell
$ npm install --save cli-list
```

## Usage
```javascript
var list = require('cli-list');
var opts = list(process.argv.slice(2));
```

ES6 + Minimist:
```javascript
import list from 'cli-list';
import minimist from 'minimist';
const opts = list(process.argv.slice(2)).map(item => minimist(item));
```

## Examples
Given:
```
$ test foo --bar, baz, --qux
```
Expect:
```
[['foo', '--bar'], ['baz'], ['--qux']]
```

## Credits
| ![jamen][avatar] |
|:---:|
| [Jamen Marzonie][github] |

  [avatar]: https://avatars.githubusercontent.com/u/6251703?v=3&s=125
  [github]: https://github.com/jamen
